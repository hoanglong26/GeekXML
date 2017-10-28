<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <table>
            <tbody id="remove-tag">
                <xsl:for-each select="//*[local-name()='game']">
                    <tr class="gameRow">
                        <td>
                            <xsl:value-of select="./*[local-name()='id']" />
                        </td>
                        <td>
                            <img class="gameAvt">
                                <xsl:attribute name="src">
                                    <xsl:value-of select="./*[local-name()='thumbnail']" />
                                </xsl:attribute>
                            </img>
                            <input type="hidden">
                                <xsl:attribute name="data-name">
                                    <xsl:value-of select="./*[local-name()='name']" />
                                </xsl:attribute>
                                <xsl:attribute name="data-platform">
                                    <xsl:value-of select="./*[local-name()='platform']" />
                                </xsl:attribute>
                                <xsl:attribute name="data-score">
                                    <xsl:value-of select="./*[local-name()='overallScore']" />
                                </xsl:attribute>
                                <xsl:attribute name="data-pulisher">
                                    <xsl:value-of select="./*[local-name()='pulisherAndReleaseDate']" />
                                </xsl:attribute>
                                <xsl:attribute name="data-link">
                                    <xsl:value-of select="./*[local-name()='link']" />
                                </xsl:attribute>
                                <xsl:attribute name="data-vote">
                                    <xsl:value-of select="./*[local-name()='totalVote']" />
                                </xsl:attribute>
                            </input>
                        </td>
                        <td>
                            <h4>
                                <xsl:value-of select="./*[local-name()='name']" />
                            </h4>
                            <xsl:for-each select="./*[local-name()='gameRating']">
                                <input type="hidden">
                                    <xsl:attribute name="data-id">
                                        <xsl:value-of select="./*[local-name()='id']" />
                                    </xsl:attribute>
                                    <xsl:attribute name="data-score">
                                        <xsl:value-of select="./*[local-name()='score']" />
                                    </xsl:attribute>
                                    <xsl:attribute name="data-reviewer">
                                        <xsl:value-of select="./*[local-name()='reviewer']" />
                                    </xsl:attribute>
                                    <xsl:attribute name="data-reviewdate">
                                        <xsl:value-of select="./*[local-name()='reviewedDate']" />
                                    </xsl:attribute>
                                </input>
                            </xsl:for-each>
                        </td>
                        <td>
                            <xsl:value-of select="./*[local-name()='platform']" />
                        </td>
                        <td>
                            <h4>
                                <xsl:value-of select="./*[local-name()='overallScore']" />
                            </h4>
                        </td>
                    </tr>
        
                    <!--<div>-->
                    <!--<xsl:value-of select="name" />-->
                    <!--</div>-->
                    <!--</a>-->
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>
</xsl:stylesheet>

